import { useState } from "react";

function AddPatient() {
    const [form, setForm] = useState({
        name: "",
        dateOfBirth: "",
        phone: "",
        email: "",
    });

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleChange = (event) => {
        setForm({
            ...form,
            [event.target.name]: event.target.value,
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        setMessage("");
        setError("");

        try {
            const response = await fetch(
                "http://localhost:8080/api/patients",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        name: form.name,
                        dateOfBirth: form.dateOfBirth,
                        phone: form.phone,
                        email: form.email,
                    }),
                }
            );

            if (!response.ok) {
                throw new Error("Unable to add patient");
            }

            await response.json();

            setMessage("Patient added successfully!");

            setForm({
                name: "",
                dateOfBirth: "",
                phone: "",
                email: "",
            });
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div style={styles.page}>
            <div style={styles.container}>

                <h1 style={styles.title}>Add Patient</h1>

                <p style={styles.subtitle}>
                    Register a new patient in CarePlan
                </p>

                <form onSubmit={handleSubmit} style={styles.form}>

                    <label>Patient Name</label>
                    <input
                        type="text"
                        name="name"
                        value={form.name}
                        onChange={handleChange}
                        required
                    />

                    <label>Date of Birth</label>
                    <input
                        type="date"
                        name="dateOfBirth"
                        value={form.dateOfBirth}
                        onChange={handleChange}
                        required
                    />

                    <label>Phone Number</label>
                    <input
                        type="text"
                        name="phone"
                        value={form.phone}
                        onChange={handleChange}
                        required
                    />

                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={form.email}
                        onChange={handleChange}
                        required
                    />

                    <button type="submit" style={styles.button}>
                        Add Patient
                    </button>

                </form>

                {message && (
                    <p style={styles.success}>{message}</p>
                )}

                {error && (
                    <p style={styles.error}>{error}</p>
                )}

            </div>
        </div>
    );
}

const styles = {
    page: {
        minHeight: "100vh",
        backgroundColor: "#f4f6f8",
        padding: "40px 20px",
        fontFamily: "Arial, sans-serif",
    },

    container: {
        maxWidth: "600px",
        margin: "0 auto",
        backgroundColor: "white",
        padding: "30px",
        borderRadius: "12px",
        boxShadow: "0 2px 8px rgba(0, 0, 0, 0.08)",
    },

    title: {
        margin: "0",
    },

    subtitle: {
        color: "#666",
        marginBottom: "25px",
    },

    form: {
        display: "flex",
        flexDirection: "column",
        gap: "10px",
    },

    button: {
        marginTop: "15px",
        padding: "12px",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
        fontSize: "16px",
    },

    success: {
        marginTop: "20px",
    },

    error: {
        marginTop: "20px",
    },
};

export default AddPatient;