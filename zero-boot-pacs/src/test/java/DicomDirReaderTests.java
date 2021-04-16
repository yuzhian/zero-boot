import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.media.DicomDirReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author yuzhian
 */
public class DicomDirReaderTests {
    @Test
    public void readDicomDir() {
        try(DicomDirReader reader = new DicomDirReader(new File(""))) {
            for (Attributes patient = reader.readFirstRootDirectoryRecord(); patient != null; patient = reader.findNextPatientRecord(patient)) {
                System.out.println(patient.getString(Tag.PatientID));
                for (Attributes study = reader.findStudyRecord(patient); study != null; study = reader.findNextStudyRecord(study)) {
                    System.out.println(study.getString(Tag.StudyID));
                    for (Attributes series = reader.findSeriesRecord(study); series != null; series = reader.findNextSeriesRecord(series)) {
                        System.out.println(series.getString(Tag.SeriesNumber));
                        System.out.println(series.getString(Tag.Modality));
                        System.out.println(series.getString(Tag.BodyPartExamined));
                        for (Attributes image = reader.findLowerInstanceRecord(series, false); image != null; image = reader.findNextInstanceRecord(image, false)) {
                            System.out.println(image.getString(Tag.InstanceNumber));
                        }
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }
}
