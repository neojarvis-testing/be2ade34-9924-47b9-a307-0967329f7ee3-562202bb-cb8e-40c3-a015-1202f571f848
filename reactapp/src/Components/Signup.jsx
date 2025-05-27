
import React, {useState} from "react";
import api from '../apiConfig';
import { useNavigate } from "react-router-dom";

function Signup(){
    const[formData,setFormData]=useState({
        name: '',
        email:'',
        password:'',
        confirmPassword: '',
    });

    const [errors,setErrors]=useState({});
    const navigate=useNavigate();
    const handleChange=(e)=>{
        setFormData({ ...formData,[e.target.name]:e.target.value});
        setErrors({ ...errors,[e.target.name]:''});

    };
    const handleSubmit =async(e)=>{
        e.preventDefault();
        let errs={};
        if(!formData.name)errs.name= 'Name is required';
        if(!formData.email) errs.email='Email is required';
        if(!formData.password) errs.password='Password is required';
        if(!formData.confirmPassword) errs.confirmPassword='Confirm Password is required';
        if(formData.password &&
            formData.confirmPassword &&
            formData.password !== formData.confirmPassword ) {
            errs.confirmPassword='Passwords do not match';

        }
        setErrors(errs);
        if(Object.keys(errs).length>0){
            
            return;
        }
        try {
            await api.post('/auth/signup',{
                name: formData.name,
                email:formData.email,
                password: formData.password,
            });
            navigate('/login');
        }catch(err){
            alert('Signup failed. Try again.');
        }
    };
    return (
        <div>
            <h2>Signup</h2>
            <form onSubmit={handleSubmit} noValidate>
                <input 
                type="text"
                name="name"
                placeholder="Name"
                value={formData.name}
                onChange={handleChange}
                data-testid="name-input"
                />
                {errors.name && <div data-testid="name-error" style={{color: 'red'}}>{errors.name}</div>}
                <br/>

            <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            data-testid="email-input"
            />
           {errors.email && <div data-testid="email-error" style={{color: 'red'}}>{errors.email}</div>}

            <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            data-testid="password-input"
            />

           {errors.password && <div data-testid="password-error" style={{color: 'red'}}>{errors.password}</div>}

            <input
            type="password"
            name="confirmPassword"
            placeholder="Confirm Password"
            value={formData.confirmPassword}
            onChange={handleChange}
            data-testid="confirmPassword-input"
            />
            {errors.confirmPassword && <div data-testid="confirmPassword-error" style={{color: 'red'}}>{errors.confirmPassword}</div>}

            <button type ="submit">Signup</button>
            </form>
        </div>
    );
            

        }

export default Signup;