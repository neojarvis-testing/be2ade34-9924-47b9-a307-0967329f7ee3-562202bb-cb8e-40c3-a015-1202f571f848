import React, {useState} from "react";
function OpenRDForm(){
    const[formData,setFormData]=useState({
        monthlyDeposit:"",
        tenure:""
    });
    const[errors,setErrors]=useState({
    });
    const handleChange=(e)=>{
        setFormData({ ...formData,[e.target.name]: e.target.value});
    };
    const validate=()=>{
        const newErrors={};
        if(!formData.monthlyDeposit.trim()){
            newErrors.monthlyDeposit="Monthly Deposit is required";

        }
        if(!formData.tenure.trim()){
            newErrors.tenure="Tenure is required";
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length===0;

        };
        const handleSubmit=(e)=>{
            e.preventDefault();
            if(validate()){
                console.log("RD Submitted", formData);
            }
        };
        return(
            <div>
                <h2 data-testid="open-rd-form-heading">Open Recurring Deposit</h2>
                <form onSubmit={handleSubmit}noValidate>
                    <div>
                        <label>Monthly Deposit:</label>
                        <input name="monthlyDeposit"
                        value={formData.monthlyDeposit}
                         onChange={handleChange}
                         data-testid="monthly-deposit-input"
                         />
                         
                            {errors.monthlyDeposit && (
                                <span data-testid="monthly-deposit-error" style={{color:"red"}}>
                                {errors.monthlyDeposit}
                                </span>
                            )}
                         </div>
                         <div>
                            <label>Tenure (in months):</label>
                            <input
                            name="tenure"
                            value={formData.tenure}
                            onChange={handleChange}
                            data-testid="tenure-input"
                            />
                            {errors.monthlyDeposit && (
                                <span data-testid="monthly-deposit-error" style={{color:"red"}}>
                                {errors.monthlyDeposit}
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
export default OpenRDForm;