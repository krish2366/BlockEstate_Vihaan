import axios from "axios";
import React, { useState } from "react";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

const RegisterLandForm = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    propertyId: "",
    locationDescription: "",
    latitudeLongitude: "",
    areaSqMeters: "",
    ownerWalletAddress: "",
    tokenURI: "",
    registrationDate: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    try {
      e.preventDefault();
      console.log("Form Data:", formData);
      const { data } = await axios.post(
        "http://localhost:8080/Land/RegisterLand",
        formData 
      );
      if (data) {
        toast.success("register successful");
        navigate("/userDetails");
      } else {
        toast.error("some error occured while registration");
      }
    } catch (error) {
      toast.error(error.message);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-blue-100 via-blue-200 to-blue-300 flex items-center justify-center p-4">
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-3xl bg-white p-8 rounded-2xl shadow-xl space-y-8"
      >
        <h2 className="text-3xl font-bold text-center text-blue-700 mb-4">
          🏡 Property Registration
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Property ID */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Property ID <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="propertyId"
              value={formData.propertyId}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Location Description */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Location Description
            </label>
            <input
              type="text"
              name="locationDescription"
              value={formData.locationDescription}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Latitude & Longitude */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Latitude & Longitude <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="latitudeLongitude"
              value={formData.latitudeLongitude}
              onChange={handleChange}
              placeholder="e.g. 28.6139, 77.2090"
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Area in sq meters */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Area (sq meters) <span className="text-red-500">*</span>
            </label>
            <input
              type="number"
              name="areaSqMeters"
              value={formData.areaSqMeters}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Owner Wallet Address */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Owner Wallet Address <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="ownerWalletAddress"
              value={formData.ownerWalletAddress}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Token URI */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Token URI
            </label>
            <input
              type="text"
              name="tokenURI"
              value={formData.tokenURI}
              onChange={handleChange}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Registration Date */}
          <div>
            <label className="block mb-1 font-medium">Registration Date</label>
            <input
              type="datetime-local"
              name="registrationDate"
              value={formData.registrationDate}
              onChange={handleChange}
              required
              className="w-full p-2 border rounded-md"
            />
          </div>
        </div>

        <div className="text-center pt-4">
          <button
            type="submit"
            className="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-3 rounded-lg shadow-md transition duration-300"
          >
            🚀 Submit Property
          </button>
        </div>
      </form>
    </div>
  );
};

export default RegisterLandForm;
