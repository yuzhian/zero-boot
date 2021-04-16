package com.github.yuzhian.zero.boot.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(title = "Node", description = "节点内容")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Node<T> {
        @Schema(title = "标签内容")
        private String label;

        @Schema(title = "标签取值")
        private String value;

        @JsonIgnore
        @Schema(hidden = true)
        private String parent;

        @Schema(title = "节点全部信息")
        private T entity;

        @Schema(title = "子节点")
        private List<Node<T>> children;
    }
}
