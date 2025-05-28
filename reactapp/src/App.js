import React from "react";
import { BrowserRouter as Router,Routes,Route } from "react-router-dom";
import Home from "./Components/HomePage";
import ViewAllAccountsComponent from "./ManagerComponents/ViewAllAccountsComponent";
function App(){
    return (
        <Router>
            <Routes>
                <Home/>
                <Route path="/manager/accounts" element={<ViewAllAccountsComponent/>}/>
            </Routes>
        </Router>
    );
}
export default App;