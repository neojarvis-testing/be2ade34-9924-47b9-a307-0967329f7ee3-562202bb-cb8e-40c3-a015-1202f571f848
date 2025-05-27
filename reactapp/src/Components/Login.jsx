import React,{useState} from "react";
import api from "../apiConfig"
import {useNavigate } from 'react-router-dom';
function Login(){
    const [email,setEmail]=useState('');
    const [password,setPassword]=useState('');
    const [errors,setErrors]=useState({});
    const navigate=useNavigate();

    const handleLogin=async(e)=>{
        e.preventDefault();
        let errs={};
        if(!email) errs.email='Email is required';
        if(!password)errs.password='Password is required';
        if(Object.keys(errs).length>0){
            setErrors(errs);
            return;
        }
        try{
            const res=await api.post('/auth/login', {email,password});
            localStorage/setItem('token',res.data.token);
            navigate('/dashboard');
        }catch(err){
            alert('Invalid creditionals');
        }
        
    };
    return (
        <div> 
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
        <input type="email" 
        placeholder="Email" 
        value ={email} 
        onChange={(e)=>{
            setEmail(e.target.value);
            setErrors({...errors, email: ''});
        }} 
        />
        <div style={{color:'red'}}>{errors.email}</div>
        <br/>
        <input 
        type="password" 
        placeholder="Password"
        value ={password}
        onChange ={(e)=>{
        setPassword(e.target.value);
        setErrors({ ...errors,password: '' }) 
        }}
        />
         <div style={{color:'red'}}>{errors.password}</div>
        <br/>
        <button type="submit">Login </button>
        </form>
        </div>
        );
    }
export default Login;