import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import axios from "axios";

const TransferDetails = () => {
  const { propertyId } = useParams();
  const location = useLocation();

  const fromAddress = location.state?.fromAddress;
  const toAddress = location.state?.toAddress;

  const [landData, setLandData] = useState(null);

  useEffect(() => {
    const fetchLandDetails = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/Land/getLandDetails/${propertyId}`);
        setLandData(res.data);
      } catch (err) {
        console.error("Failed to fetch land details:", err);
      }
    };
    fetchLandDetails();
  }, [propertyId]);

  if (!landData) return <div className="text-center mt-10 text-lg font-semibold">Loading...</div>;

  const fromUser = {
    ...landData,
    walletType: "From User Wallet",
    walletAddress: fromAddress,
  };

  const toUser = {
    ...landData,
    walletType: "To User Wallet",
    walletAddress: toAddress,
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-indigo-100 via-purple-100 to-pink-100 flex items-center justify-center p-6">
      <div className="flex flex-col md:flex-row gap-8 items-center">
        <Card title="👤 From Owner" data={fromUser} bgColor="bg-purple-600" />
        <div className="text-3xl md:text-5xl text-purple-800 font-bold">➡️</div>
        <Card title="👤 To Owner" data={toUser} bgColor="bg-green-600" />
      </div>
    </div>
  );
};

const Card = ({ title, data, bgColor }) => (
  <div className={`w-full max-w-sm p-6 rounded-xl shadow-xl ${bgColor} text-white`}>
    <h3 className="text-2xl font-bold mb-4">{title}</h3>
    <div className="space-y-2">
      <p><span className="font-semibold">Property ID:</span> {data.propertyId}</p>
      {data.locationDescription && (
        <p><span className="font-semibold">Location:</span> {data.locationDescription}</p>
      )}
      <p><span className="font-semibold">Coordinates:</span> {data.latitudeLongitude}</p>
      <p><span className="font-semibold">Area:</span> {data.areaSqMeters} sq. meters</p>
      <p><span className="font-semibold">{data.walletType}:</span> {data.walletAddress}</p>
      {data.tokenURI && (
        <p><span className="font-semibold">Token URI:</span> {data.tokenURI}</p>
      )}
      <p><span className="font-semibold">Registered On:</span> {new Date(data.registrationDate).toLocaleDateString()}</p>
    </div>
  </div>
);

export default TransferDetails;
