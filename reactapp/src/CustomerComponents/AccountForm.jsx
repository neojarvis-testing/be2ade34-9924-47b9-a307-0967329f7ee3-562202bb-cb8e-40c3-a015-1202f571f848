import React,{useState} from "react";

function AccountForm(){
    const[formData,setFormData]=useState({
        holderName:"",
        initialDeposit:""
    });
    const[errors,setErrors]=useState({});
    const handleChange=(e)=>{
        setFormData({ ...formData,[e.target.name]: e.target.value});

    };

    const validate=()=>
    {
        const newErrors={};
        if(!formData.holderName.trim()){
            newErrors.holderName="Holder Name is required";
        }
        if(!formData.initialDeposit.trim()){
            newErrors.initialDeposit="Initial Deposit is required";

        }
        setErrors(newErrors);
        return Object.keys(newErrors).length===0;
        };
        const handleSubmit=(e)=>{
            e.preventDefault();
            if(validate()){
                console.log("Submitted", formData);
            }
        };
        return(
            <div>
                <h2 data-testid="account-form-heading">New Account</h2>
                <form onSubmit={handleSubmit} noValidate>
                    <div>
                        <label>Holder Name:
                        </label>
                        <input name="holderName"
                        value={formData.holderName}
                        onChange={handleChange}
                        data-testid="holder-name-input"
                        />
                        {errors.holderName && (
                            <span data-testid="holder-name-error" style={{color: 'red'}}>
                            {errors.holderName}</span>

                        )}
                    </div>
                    <div>
                        <label>Initial Deposit:</label>
                        <input name ="initialDeposit"
                        value={formData.initialDeposit}
                        onChange={handleChange}
                        data-testid="initial-deposit-input"
                    />
                    {errors.initialDeposit &&(
                        <span data-testid="initial-deposit-error" style={{color: 'red'}}>{errors.initialDeposit}
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
export default AccountForm;