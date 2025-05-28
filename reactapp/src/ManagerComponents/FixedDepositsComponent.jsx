import React, { useEffect } from "react";

function FixedDepositComponent(){
    const [fixedDeposits, setFixedDeposits]=useState([]);
    const[error,setError]=useState(null);
    useEffect(()=>{
        fetch("/api/fixed-deposits")
        .then((res)=>{
            if(!res.ok)throw new Error("Failed to fetch");
            return res.json();
        })
        .then((data)=> setFixedDeposits(data))
        .catch((err)=>{
            setError("Error fetching fixed deposits.");
            console.error(err);
        });
    },[]);
    } return (

        
        <div>
          
            <h1 data-testid="fixed-deposit-heading">All Accounts</h1>
            {error && <p data-testid="error-message">{error}</p>}

            <table data-testid="fixed-deposit-table" border="1">
                <thead>
                    <tr>
                        <th>FD ID</th>
                        <th>Account ID</th>
                        <th>Principal Amount</th>
                        <th>Interest Rate</th>
                        <th>Tenure</th>
                        <th>Maturity Amount</th>
                        <th>Status</th>
                        <th>Date Created</th>
                        <th>Date Closed</th>
                        <th>Actions</th>
                    

                    </tr>
                </thead>
                <tbody>
                    {fixedDeposits.map((fd)=>(
                        <tr key={fd.id}>
                            <td>{fd.id}</td>
                            <td>{fd.accountId}</td>
                            <td>{fd.principal}</td>
                            <td>{fd.interestRate}</td>
                            <td>{fd.tenure}</td>
                            <td>{fd.maturityAmount}</td>
                            <td>{fd.status}</td>

                            <td>{fd.dateCreated}</td>
                            <td>{fd.dateClosed}</td>
                            <td>
                                {/*Action buttons or popup triggers go here */}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );                
export default FixedDepositComponent;