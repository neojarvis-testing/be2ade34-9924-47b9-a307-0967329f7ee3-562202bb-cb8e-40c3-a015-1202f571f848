
import React, {useState} from "react";
import api from '../apiConfig';
import { useNavigate } from "react-router-dom";

function Signup(){
    const[formData,setFormData]=useState({
        name: '',
        email:'',
        mobile:'',
        password:'',
        confirmPassword: '',
        role:''
    });

    const [errors,setErrors]=useState({});
    const navigate=useNavigate();
    const emailRegex= /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const mobileRegex=/^\d{10}$/;

    const handleChange=(e)=>{
        setFormData({ ...formData,[e.target.name]:e.target.value});
        setErrors({ ...errors,[e.target.name]:''});

    };
    const handleSubmit =async(e)=>{
        e.preventDefault();
        let errs={};

       
        if(!formData.name)errs.name= 'Name is required';
        if(!formData.email) errs.email='Email is required';
        else if (!emailRegex.test(formData.email))errs.email='Invalid email format';
        if(!formData.mobile)errs.mobile='Mobile number is required';
        else if(mobileRegex.test(formData.mobile))errs.mobile='Mobile number must be exactly 10 digits';
        if(!formData.password) errs.password='Password is required';
        else if (formData.password.length<6)errs.password='Password must be atleast 6 characters';
        if(!formData.confirmPassword) errs.confirmPassword='Confirm Password is required';
        else if (formData.password!==formData.confirmPassword)errs.confirmPassword='Something went wrong, Please try with different data';

        if(!formData.role)errs.role='Role is required';
        
        setErrors(errs);
        if(Object.keys(errs).length>0){
            
            return;
        }
        try {
            await api.post('/auth/signup',{
                name: formData.name,
                email:formData.email,
                mobile:formData.mobile,
                password: formData.password,
                role: formData.role
            });
            navigate('/login');
        }catch(err){
            alert('Something went wrong, Please try with different data');
        }
    };
    return (
        <div>
            <h2 data-testid="signup-heading">Signup</h2>
            <form onSubmit={handleSubmit} noValidate>
                <input 
                type="text"
                name="name"
                placeholder="Name"
                value={formData.name}
                onChange={handleChange}
                data-testid="name-input"
                />
               <p role="alert" data-testid="name-error">{errors.name}</p>
            <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            data-testid="email-input"
            />
          <p role="alert" data-testid="email-error">{errors.email}</p>

           <input
            type="text"
            name="mobile"
            placeholder="Mobile Number"
            value={formData.mobile}
            onChange={handleChange}
            data-testid="mobile-input"
            />
           <p role="alert" data-testid="mobile-error">{errors.mobile}</p>
            <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            data-testid="password-input"
            />

<p role="alert" data-testid="password-error">{errors.password}</p>

            <input
            type="password"
            name="confirmPassword"
            placeholder="Confirm Password"
            value={formData.confirmPassword}
            onChange={handleChange}
            data-testid="confirmPassword-input"
            />
            <p role="alert" data-testid="confirmPassword-error">{errors.confirmPassword}</p>

            <select 
            name="role"
            value={formData.role}
            onChange={handleChange}
            data-testid="role-select">
                <option value="">Select Role</option>
                <option value="Customer">Customer</option>
                <option value="Teller">Teller</option>
                <option value="Manager">Manager</option>

            </select>
            <p role="alert" data-testid="role-error">{errors.role}</p>
            <button type ="submit" data-testid="submit-button">Signup</button>
            </form>
        </div>
    );
            

        }

export default Signup;