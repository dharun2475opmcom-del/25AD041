package care.plan.service;

import care.plan.model.Medicine;
import care.plan.model.Patient;
import care.plan.repository.MedicineRepository;
import care.plan.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final PatientRepository patientRepository;

    public MedicineService(
            MedicineRepository medicineRepository,
            PatientRepository patientRepository) {

        this.medicineRepository = medicineRepository;
        this.patientRepository = patientRepository;
    }

    public Medicine createMedicine(Long patientId, Medicine medicine) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        medicine.setPatient(patient);

        return medicineRepository.save(medicine);
    }

    public List<Medicine> getMedicinesByPatient(Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return medicineRepository.findByPatientId(patientId);
    }

    public Medicine getMedicineById(Long id) {

        return medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
    }

    public Medicine updateMedicine(Long id, Medicine medicine) {

        Medicine existingMedicine = getMedicineById(id);

        existingMedicine.setName(medicine.getName());
        existingMedicine.setDosage(medicine.getDosage());
        existingMedicine.setDescription(medicine.getDescription());

        return medicineRepository.save(existingMedicine);
    }

    public void deleteMedicine(Long id) {

        Medicine existingMedicine = getMedicineById(id);

        medicineRepository.delete(existingMedicine);
    }
}