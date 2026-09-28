package care.plan.controller;

import care.plan.model.Medicine;
import care.plan.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/patients/{patientId}/medicines")
    public Medicine createMedicine(
            @PathVariable Long patientId,
            @RequestBody Medicine medicine) {

        return medicineService.createMedicine(patientId, medicine);
    }

    @GetMapping("/patients/{patientId}/medicines")
    public List<Medicine> getMedicinesByPatient(
            @PathVariable Long patientId) {

        return medicineService.getMedicinesByPatient(patientId);
    }

    @GetMapping("/medicines/{id}")
    public Medicine getMedicineById(@PathVariable Long id) {

        return medicineService.getMedicineById(id);
    }

    @PutMapping("/medicines/{id}")
    public Medicine updateMedicine(
            @PathVariable Long id,
            @RequestBody Medicine medicine) {

        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/medicines/{id}")
    public String deleteMedicine(@PathVariable Long id) {

        medicineService.deleteMedicine(id);

        return "Medicine deleted successfully";
    }
}