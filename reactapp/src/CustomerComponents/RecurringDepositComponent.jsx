import React from "react";

function RecurringDepositComponent(){
    const recurringDeposits=[
        {id:1,month:"January",amount:1000},
        {id:2,month:"February",amount:1000},
        {id:3,month:"March",amount:1000},
    ];
    
    return(
        <div>
            <h1 data-testid="recurring-deposit-heading">Your Recurring Deposits</h1>
            <table data-testid="recurring-deposit-table" border="1">
                <thead>
                   <tr>
                    <th>ID</th>
                    <th>Month</th>
                    <th>Amount</th>
                    </tr>

                    
                </thead>
                <tbody>
                    {recurringDeposits.map((rd)=>(
                        <tr key={rd.id}>
                            <td>{rd.id}</td>
                            <td>{rd.month}</td>
                            <td>{rd.amount}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
export default RecurringDepositComponent;