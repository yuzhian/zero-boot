import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author yuzhian
 */
public class DicomReaderTests {
    @Test
    public void readDicom() {
        try (DicomInputStream dis = new DicomInputStream(new FileInputStream(""))) {
            // Attributes fmi = dis.readFileMetaInformation();
            Attributes dataset = dis.readDataset();
            System.out.println(dataset.getString(Tag.PatientID));
            System.out.println(dataset.getString(Tag.StudyID));
            System.out.println(dataset.getString(Tag.ClinicalTrialSeriesID));
            System.out.println(dataset.getString(Tag.ImageID));
            System.out.println(dataset.getString(Tag.Modality));
        } catch (IOException ignored) {
        }
    }
}
