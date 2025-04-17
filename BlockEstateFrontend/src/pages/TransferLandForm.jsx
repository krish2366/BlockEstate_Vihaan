import React, { useState } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const TransferForm = () => {
  const [formData, setFormData] = useState({
    propertyId: "",
    tokenId: "",
    fromAddress: "",
    toAddress: "",
    transferTimestamp: "",
  });

  const privateKey = "3843be5dcfe61ece0a43b244fd4f42c6e2ca2abdb9a0f1820f666bb97ebcd273"

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const navigate = useNavigate();

const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const { data } = await axios.post(
      `http://localhost:8080/Land-transfer/transfer?privateKey=${privateKey}`,
      formData
    );
    if (data) {
      toast.success("Transfer recorded successfully!");
      navigate(`/Land/getLandDetails/${formData.propertyId}`, {
        state: {
          fromAddress: formData.fromAddress,
          toAddress: formData.toAddress,
        },
      });
    } else {
      toast.error("Failed to submit transfer data.");
    }
  } catch (error) {
    toast.error(`${error.message}`);
  }
};

  // const handleSubmit = async (e) => {
  //   e.preventDefault();
  //   try {
  //     const data = await axios.post(
  //       `http://localhost:8080/Land-transfer/transfer?privateKey=${privateKey}`,
  //       formData
  //     );
  //     if (data) {
  //       toast.success("Transfer recorded successfully!");
  //     } else {
  //       toast.error("Failed to submit transfer data.");
  //     }
  //   } catch (error) {
  //     toast.error(`${error.message}`);
  //   }
  // };

  return (
    <div className="min-h-screen bg-gradient-to-r from-green-100 via-green-200 to-green-300 flex items-center justify-center p-4">
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-2xl bg-white p-8 rounded-2xl shadow-lg space-y-8"
      >
        <h2 className="text-3xl font-bold text-center text-green-700 mb-4">
          🔄 Property Transfer Form
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
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* Token ID */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              Token ID
            </label>
            <input
              type="number"
              name="tokenId"
              value={formData.tokenId}
              onChange={handleChange}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* From Address */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              From Address <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="fromAddress"
              value={formData.fromAddress}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* To Address */}
          <div>
            <label className="block text-gray-700 font-semibold mb-1">
              To Address <span className="text-red-500">*</span>
            </label>
            <input
              type="text"
              name="toAddress"
              value={formData.toAddress}
              onChange={handleChange}
              required
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          {/* Transfer Timestamp */}
          <div className="md:col-span-2">
            <label className="block text-gray-700 font-semibold mb-1">
              Transfer Timestamp
            </label>
            <input
              type="datetime-local"
              name="transferTimestamp"
              value={formData.transferTimestamp}
              onChange={handleChange}
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>
        </div>

        <div className="text-center pt-4">
          <button
            type="submit"
            className="bg-green-600 hover:bg-green-700 text-white font-semibold px-6 py-3 rounded-lg shadow-md transition duration-300"
          >
            💾 Submit Transfer
          </button>
        </div>
      </form>
    </div>
  );
};

export default TransferForm;
