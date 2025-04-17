import React, { useState } from "react";
import toast from "react-hot-toast";
import { NavLink, useNavigate } from "react-router-dom";
import axios from "axios";

const SignUp = () => {
  const [username, setUsername] = useState("");
  const [ownerWalletAddress, setOwnerWalletAddress] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");

  const navigate = useNavigate();

  const onHandleSubmit = async (e) => {
    try {
        // console.log(username + " " + ownerWalletAddress+" "+password+" "+role);
      e.preventDefault();

      const { token } = await axios.post(
        "http://localhost:8080/User/register",
        { username, ownerWalletAddress, password, role }
      );
      if (token.data) {
        toast.success("login success");
        console.log("login success");
        navigate("/");
      } else {
        toast.error("login failed");
        console.log("login failed");
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 px-4">
      <form
        onSubmit={onHandleSubmit}
        className="max-w-md w-full text-center border border-white/30 backdrop-blur-md bg-white/30 px-8 py-10 rounded-2xl shadow-xl"
      >
        <h1 className="text-gray-900 text-3xl font-semibold">Sign Up</h1>
  
        {/* Username */}
        <div className="flex items-center w-full mt-10 bg-white/60 border border-gray-300/50 h-12 rounded-full overflow-hidden pl-6 gap-2">
          <span className="text-gray-500">@</span>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
            className="bg-transparent text-gray-700 placeholder-gray-500 outline-none text-sm w-full h-full"
            required
          />
        </div>
  
        {/* Wallet Address */}
        <div className="flex items-center w-full mt-6 bg-white/60 border border-gray-300/50 h-12 rounded-full overflow-hidden pl-6 gap-2">
          <svg
            width="16"
            height="11"
            viewBox="0 0 16 11"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M0 .55.571 0H15.43l.57.55v9.9l-.571.55H.57L0 10.45zm1.143 1.138V9.9h13.714V1.69l-6.503 4.8h-.697zM13.749 1.1H2.25L8 5.356z"
              fill="#6B7280"
            />
          </svg>
          <input
            type="text"
            value={ownerWalletAddress}
            onChange={(e) => setOwnerWalletAddress(e.target.value)}
            placeholder="Wallet Address"
            className="bg-transparent text-gray-700 placeholder-gray-500 outline-none text-sm w-full h-full"
            required
          />
        </div>
  
        {/* Role */}
        <div className="flex items-center mt-6 w-full bg-white/60 border border-gray-300/50 h-12 rounded-full overflow-hidden pl-6 pr-3 gap-2">
          <select
            value={role}
            onChange={(e) => setRole(e.target.value)}
            className="bg-transparent text-gray-700 placeholder-gray-500 outline-none text-sm w-full h-full"
            required
          >
            <option value="" disabled hidden>
              Role
            </option>
            <option value="superadmin">Super Admin</option>
            <option value="registrar">Registrar</option>
            <option value="user">User</option>
          </select>
        </div>
  
        {/* Password */}
        <div className="flex items-center mt-6 w-full bg-white/60 border border-gray-300/50 h-12 rounded-full overflow-hidden pl-6 gap-2">
          <svg
            width="13"
            height="17"
            viewBox="0 0 13 17"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M13 8.5c0-.938-.729-1.7-1.625-1.7h-.812V4.25C10.563 1.907 8.74 0 6.5 0S2.438 1.907 2.438 4.25V6.8h-.813C.729 6.8 0 7.562 0 8.5v6.8c0 .938.729 1.7 1.625 1.7h9.75c.896 0 1.625-.762 1.625-1.7zM4.063 4.25c0-1.406 1.093-2.55 2.437-2.55s2.438 1.144 2.438 2.55V6.8H4.061z"
              fill="#6B7280"
            />
          </svg>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="bg-transparent text-gray-700 placeholder-gray-500 outline-none text-sm w-full h-full"
            required
          />
        </div>
  
        {/* Submit */}
        <button
          type="submit"
          className="mt-6 w-full h-11 rounded-full text-white bg-indigo-500 hover:bg-indigo-600 transition"
        >
          Sign Up
        </button>
  
        {/* Login redirect */}
        <p className="text-gray-700 text-sm mt-4">
          Already have an account?{' '}
          <NavLink to="/login" className="text-indigo-500 hover:underline">
            Login
          </NavLink>
        </p>
      </form>
    </div>
  );  
};

export default SignUp;
