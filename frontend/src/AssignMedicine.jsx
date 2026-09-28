import { useEffect, useState } from "react";

function AssignMedicine() {
    const [patients, setPatients] = useState([]);

    const [form, setForm] = useState({
        patientId: "",
        name: "",
        dosage: "",
        description: "",
        frequency: "DAILY",
        scheduledTime: "",
        startDate: "",
        endDate: ""
    });

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8080/api/patients")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Unable to load patients");
                }

                return response.json();
            })
            .then((data) => {
                setPatients(data);
            })
            .catch((error) => {
                setError(error.message);
            });
    }, []);

    const handleChange = (event) => {
        setForm({
            ...form,
            [event.target.name]: event.target.value
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        setMessage("");
        setError("");
        setLoading(true);

        try {
            // Create medicine
            const medicineResponse = await fetch(
                `http://localhost:8080/api/patients/${form.patientId}/medicines`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        name: form.name,
                        dosage: form.dosage,
                        description: form.description
                    })
                }
            );

            if (!medicineResponse.ok) {
                throw new Error("Failed to create medicine");
            }

            const medicine = await medicineResponse.json();

            // Create schedule
            const scheduleResponse = await fetch(
                `http://localhost:8080/api/medicines/${medicine.id}/schedules`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        frequency: form.frequency,
                        scheduledTime: form.scheduledTime + ":00",
                        startDate: form.startDate,
                        endDate: form.endDate
                    })
                }
            );

            if (!scheduleResponse.ok) {
                throw new Error(
                    "Medicine created, but schedule creation failed"
                );
            }

            setMessage("Medicine assigned successfully!");

            setForm({
                patientId: "",
                name: "",
                dosage: "",
                description: "",
                frequency: "DAILY",
                scheduledTime: "",
                startDate: "",
                endDate: ""
            });

        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="assign-page">

            <div className="assign-card">

                <h1>Assign Medicine</h1>

                <p>
                    Assign medicine and schedule for a patient
                </p>

                {message && (
                    <div className="success">
                        {message}
                    </div>
                )}

                {error && (
                    <div className="error">
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit}>

                    <label>Patient</label>

                    <select
                        name="patientId"
                        value={form.patientId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">
                            Select patient
                        </option>

                        {patients.map((patient) => (
                            <option
                                key={patient.id}
                                value={patient.id}
                            >
                                {patient.name}
                            </option>
                        ))}
                    </select>

                    <label>Medicine Name</label>

                    <input
                        type="text"
                        name="name"
                        value={form.name}
                        onChange={handleChange}
                        placeholder="Paracetamol"
                        required
                    />

                    <label>Dosage</label>

                    <input
                        type="text"
                        name="dosage"
                        value={form.dosage}
                        onChange={handleChange}
                        placeholder="500 mg"
                        required
                    />

                    <label>Description</label>

                    <textarea
                        name="description"
                        value={form.description}
                        onChange={handleChange}
                        placeholder="Take after food"
                        rows="3"
                    />

                    <label>Frequency</label>

                    <select
                        name="frequency"
                        value={form.frequency}
                        onChange={handleChange}
                    >
                        <option value="DAILY">Daily</option>
                        <option value="WEEKLY">Weekly</option>
                    </select>

                    <label>Scheduled Time</label>

                    <input
                        type="time"
                        name="scheduledTime"
                        value={form.scheduledTime}
                        onChange={handleChange}
                        required
                    />

                    <div className="date-row">

                        <div>
                            <label>Start Date</label>

                            <input
                                type="date"
                                name="startDate"
                                value={form.startDate}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div>
                            <label>End Date</label>

                            <input
                                type="date"
                                name="endDate"
                                value={form.endDate}
                                onChange={handleChange}
                                required
                            />
                        </div>

                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                    >
                        {loading
                            ? "Assigning..."
                            : "Assign Medicine"}
                    </button>

                </form>

            </div>

        </div>
    );
}

export default AssignMedicine;