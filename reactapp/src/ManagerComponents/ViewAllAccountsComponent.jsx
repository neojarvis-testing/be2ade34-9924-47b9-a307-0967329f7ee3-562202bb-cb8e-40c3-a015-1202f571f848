import React, { useEffect,useState } from "react";
import  api from '../apiConfig';
function ViewAllAccountsComponent(){
    const[accounts,setAccounts]=useState([]);
    useEffect(()=>{
        api.get("/manager/accounts")
        .then(response=>setAccounts(response.data))
        .catch((error)=>console.error('Error fetching accounts:',error));
    },[]);
    return (
        <div>
            <h2 data-testid="accounts-heading">All Accounts</h2>
            {accounts.length>0 ?(
            <table data-testid="accounts-table">
                <thead>
                    <tr>
                        <th>Account Number</th>
                        <th>Holder Name</th>
                        <th>Balance</th>

                    </tr>
                    </thead>
                  <tbody>
                    {accounts.map((account) =>(
                        <tr key={account.accountNumber}>
                            <td>{account.accountNumber}</td>
                            <td>{account.holderName}</td>
                            <td>{account.balance}</td>

                        </tr>
                    ))}
                  </tbody>
                
            </table>
            ):( 
                <p data-testid="no-accounts-message">No accounts available.</p>
            )}
        </div>
    );
}

export default ViewAllAccountsComponent;