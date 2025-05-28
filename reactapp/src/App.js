import React from "react";
import { BrowserRouter as Router,Routes,Route } from "react-router-dom";

import ViewAllAccountsComponent from "./ManagerComponents/ViewAllAccountsComponent";
function App(){
    return (
        <Router>
            <Routes>
             
                <Route path="/manager/accounts" element={<ViewAllAccountsComponent/>}/>
            </Routes>
        </Router>
    );
}
export default App;