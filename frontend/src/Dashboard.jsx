import { useEffect, useState } from "react";

function Dashboard() {
    const [patients, setPatients] = useState([]);
    const [medicineCount, setMedicineCount] = useState(0);
    const [overdueDoses, setOverdueDoses] = useState([]);

    useEffect(() => {
        loadDashboardData();
    }, []);

    const loadDashboardData = async () => {
        try {
            const patientResponse = await fetch(
                "http://localhost:8080/api/patients"
            );

            if (!patientResponse.ok) {
                throw new Error("Unable to load patients");
            }

            const patientData = await patientResponse.json();
            setPatients(patientData);

            let totalMedicines = 0;

            for (const patient of patientData) {
                const medicineResponse = await fetch(
                    `http://localhost:8080/api/patients/${patient.id}/medicines`
                );

                if (medicineResponse.ok) {
                    const medicines = await medicineResponse.json();
                    totalMedicines += medicines.length;
                }
            }

            setMedicineCount(totalMedicines);

            const overdueResponse = await fetch(
                "http://localhost:8080/api/doses/overdue"
            );

            if (overdueResponse.ok) {
                const overdueData = await overdueResponse.json();
                setOverdueDoses(overdueData);
            }
        } catch (error) {
            console.error("Dashboard error:", error);
        }
    };

    return (
        <div style={styles.page}>
            <div style={styles.container}>

                <header style={styles.header}>
                    <h1 style={styles.title}>CarePlan Dashboard</h1>
                    <p style={styles.subtitle}>
                        Elderly Medicine Reminder & Tracking System
                    </p>
                </header>

                <div style={styles.cards}>

                    <div style={styles.card}>
                        <h3 style={styles.cardTitle}>Patients</h3>
                        <p style={styles.number}>{patients.length}</p>
                        <p style={styles.description}>
                            Registered patients
                        </p>
                    </div>

                    <div style={styles.card}>
                        <h3 style={styles.cardTitle}>Medicines</h3>
                        <p style={styles.number}>{medicineCount}</p>
                        <p style={styles.description}>
                            Assigned medicines
                        </p>
                    </div>

                    <div style={styles.card}>
                        <h3 style={styles.cardTitle}>Overdue Doses</h3>
                        <p style={styles.number}>{overdueDoses.length}</p>
                        <p style={styles.description}>
                            Require attention
                        </p>
                    </div>

                </div>

                <section style={styles.section}>
                    <h2 style={styles.sectionTitle}>
                        Overdue Medication
                    </h2>

                    {overdueDoses.length === 0 ? (
                        <p style={styles.empty}>
                            No overdue medicines.
                        </p>
                    ) : (
                        <div>
                            {overdueDoses.map((dose) => (
                                <div key={dose.id} style={styles.doseRow}>
                                    <div>
                                        <strong>Dose #{dose.id}</strong>
                                        <p style={styles.doseInfo}>
                                            Scheduled:{" "}
                                            {dose.scheduledDateTime}
                                        </p>
                                    </div>

                                    <span style={styles.overdue}>
                                        OVERDUE
                                    </span>
                                </div>
                            ))}
                        </div>
                    )}
                </section>

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
        maxWidth: "1100px",
        margin: "0 auto",
    },

    header: {
        marginBottom: "30px",
    },

    title: {
        margin: "0",
        fontSize: "32px",
    },

    subtitle: {
        marginTop: "8px",
        color: "#666",
        fontSize: "16px",
    },

    cards: {
        display: "grid",
        gridTemplateColumns: "repeat(3, 1fr)",
        gap: "20px",
        marginBottom: "30px",
    },

    card: {
        backgroundColor: "white",
        padding: "25px",
        borderRadius: "12px",
        boxShadow: "0 2px 8px rgba(0, 0, 0, 0.08)",
    },

    cardTitle: {
        margin: "0",
        fontSize: "18px",
    },

    number: {
        fontSize: "36px",
        fontWeight: "bold",
        margin: "15px 0 5px",
    },

    description: {
        margin: "0",
        color: "#777",
    },

    section: {
        backgroundColor: "white",
        padding: "25px",
        borderRadius: "12px",
        boxShadow: "0 2px 8px rgba(0, 0, 0, 0.08)",
    },

    sectionTitle: {
        marginTop: "0",
    },

    empty: {
        color: "#666",
    },

    doseRow: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "15px 0",
        borderBottom: "1px solid #eee",
    },

    doseInfo: {
        margin: "6px 0 0",
        color: "#666",
    },

    overdue: {
        fontWeight: "bold",
        padding: "6px 10px",
        borderRadius: "6px",
        backgroundColor: "#ffe5e5",
    },
};

export default Dashboard;