package com.github.yuzhian.zero.boot.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 递归树结构处理工具
 *
 * @author yuzhian
 */
public class TreeUtils {

    public static <T> List<Node<T>> buildTree(Collection<Node<T>> nodes) {
        List<Node<T>> tree = new ArrayList<>();
        for (Node<T> node : nodes) {
            // 顶级节点
            if (node.getParent() == null || "0".equals(node.getParent())) {
                tree.add(node);
                continue;
            }
            for (Node<T> parent : nodes) {
                if (null != node.getParent() && node.getParent().equalsIgnoreCase(parent.getValue())) {
                    if (null == parent.getChildren()) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                    break;
                }
            }
        }
        return tree;
    }

    @Getter
    @Setter
    @Builder
    @ApiModel(value = "Node", description = "节点内容")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Node<T> {
        @ApiModelProperty("标签内容")
        private String label;

        @ApiModelProperty("标签取值")
        private String value;

        @JsonIgnore
        private String parent;

        @ApiModelProperty("节点全部信息")
        private T entity;

        @ApiModelProperty("子节点")
        private List<Node<T>> children;
    }
}
