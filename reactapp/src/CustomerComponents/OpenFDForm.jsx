import React,{useState} from "react";
function OpenFDForm(){
    const[formData,setFormData]=useState({
        depositAmount:"",
        tenure:""
    });
    const[errors,setErrors]=useState({});
    const handleChange=(e)=>
    {
        setFormData({ ...formData,[e.target.name]: e.target.value});
    };
    const validate=()=>{
        const newErrors={};
        if(!formData.depositAmount.trim()){
            newErrors.depositAmount="Deposit Amount is required";

        }
        if(!formData.tenure.trim()){
            newErrors.tenure="Tenure is required";
        }
        setErrors(newErrors
            );
            return Object.keys(newErrors).length===0;
    };
    const handleSubmit=(e)=>{
        e.preventDefault();
        if(valudate()){
            console.log("FD Submitted",formData);
        }
    };
    return(
        <div>
            <h2 data-testid="open-fd-form-heading">Open Fixed Deposit</h2>
            <form onSubmit={handleSubmit}noValidate>
                <div>
                    <label>Deposit Amount:</label>
                    <input 
                    name="depositAmount"
                    value={formData.depositAmount}
                    onChange={handleChange}
                    data-testid="deposit-amount-input"
                    />
                    {errors.depositAmount &&(
                        <span data-testid="deposit-amount-error" style={{color:"red"}}>
                            {errors-depositAmount}
                        </span>

                    )}

                </div>
                <div>
                <label>Tenure(in months):</label>
                    <input 
                    name="tenure"
                    value={formData.tenure}
                    onChange={handleChange}
                    data-testid="tenure-input"
                    />
                    {errors.tenure &&(
                        <span data-testid="tenure-error" style={{color:"red"}}>
                            {errors-tenure}
                        </span>

                    )}  
                </div>
                <button type="submit" data-testid="submit-button">
                    Submit
                </button>
            </form>
        </div>
    );
}
export default OpenFDForm;