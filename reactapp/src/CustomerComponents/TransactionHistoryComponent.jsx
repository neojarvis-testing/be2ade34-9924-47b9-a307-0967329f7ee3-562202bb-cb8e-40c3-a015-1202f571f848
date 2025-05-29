import React from "react";
function TransactionHistoryComponent(){
    const transactions=[
        {id:1, date: "2024-05-01", type:"Deposit", amount:1000},
        {id:2, date:"2024-05-03", type:"Withdrawal", amount:500},
        {id:3, date: "2024-05-03", type:"Transfer", amount:700},
    ];
   
    return(
        <div>
            <h1 data-testid="transaction-history-heading">Transaction History</h1>
            <table data-testid="transaction-table" border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Amount</th>
                    </tr>
                </thead>
               <tbody>
                {transactions.map((txn)=>(
                    <tr key={txn.id}>
                        <td>{txn.id}</td>
                        <td>{txn.date}</td>
                        <td>{txn.type}</td>
                        <td>{txn.amount}</td>

                    </tr>
                    ))}
                    </tbody> 
            </table>
        </div>
    );

}
export default TransactionHistoryComponent;