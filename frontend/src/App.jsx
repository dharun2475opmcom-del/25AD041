import { useState } from "react";
import Dashboard from "./Dashboard";
import AssignMedicine from "./AssignMedicine";
import AddPatient from "./AddPatient";

function App() {
    const [page, setPage] = useState("dashboard");

    return (
        <div>
            <nav
                style={{
                    padding: "15px 30px",
                    backgroundColor: "#ffffff",
                    borderBottom: "1px solid #ddd",
                    display: "flex",
                    gap: "15px",
                }}
            >
                <button
                    onClick={() => setPage("dashboard")}
                    style={buttonStyle}
                >
                    Dashboard
                </button>

                <button
                    onClick={() => setPage("addPatient")}
                    style={buttonStyle}
                >
                    Add Patient
                </button>

                <button
                    onClick={() => setPage("assignMedicine")}
                    style={buttonStyle}
                >
                    Assign Medicine
                </button>
            </nav>

            {page === "dashboard" && <Dashboard />}

            {page === "addPatient" && <AddPatient />}

            {page === "assignMedicine" && <AssignMedicine />}
        </div>
    );
}

const buttonStyle = {
    padding: "10px 18px",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "15px",
};

export default App;