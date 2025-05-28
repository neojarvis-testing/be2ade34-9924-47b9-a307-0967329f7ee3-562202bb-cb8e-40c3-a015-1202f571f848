import React from "react";
function FixedDepositComponent(){
    const fixedDeposits=[
        {id:1,duration:"1 Year",amount:10000},
        {id:2,duration:"2 Years",amount:20000},
        {id:3,duration:"3 Years",amount:20000},
        
    ];
    return (
        <div>
            <h1 data-testid="fixed-deposit-heading">Could not paste</h1>
            <table data-testid="fixed-deposit-table" border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Duration</th>
                        <th>Amount</th>

                    </tr>
                </thead>
                <tbody>
                    {fixedDeposits.map((fd)=>(
                        <tr key={fd.id}>
                            <td>{fd.id}</td>
                            <td>{fd.duration}</td>
                            <td>{fd.amount}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default FixedDepositComponent;