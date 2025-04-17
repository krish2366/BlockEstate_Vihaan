import React, { useState } from "react";

function LandTransferChain() {
  const [propertyId, setPropertyId] = useState("");
  const [owners, setOwners] = useState([]);
  const [error, setError] = useState("");

  const fetchLandDetails = async () => {
    try {
      const res = await fetch(`http://localhost:8080/Land/getUserByPropertyId?propertyId=${propertyId}`);
      const data = await res.json();
        console.log(data[1]);
      if (Array.isArray(data)) {
        setOwners(data);
        setError("");
      } else {
        setError("No ownership data found.");
        setOwners([]);
      }
    } catch (err) {
      setError("Error fetching land details.");
      setOwners([]);
    }
    console.log(owners)
  };

  return (
    <div className="max-w-4xl mx-auto py-10 px-4">
      <h1 className="text-3xl font-bold mb-6 text-gray-800">Land Ownership Chain</h1>

      <div className="flex gap-4 mb-6">
        <input
          type="text"
          value={propertyId}
          onChange={(e) => setPropertyId(e.target.value)}
          placeholder="Enter Property ID"
          className="border p-2 rounded w-full"
        />
        <button
          onClick={fetchLandDetails}
          className="bg-emerald-600 text-white px-4 py-2 rounded hover:bg-emerald-700"
        >
          Search
        </button>
      </div>

      {error && <p className="text-red-500">{error}</p>}

      {owners.length > 0 && (
        
        <div className="space-y-6">
          {owners.map((owner, index) => (
            
            <div
              key={index}
              className="relative border-l-4 pl-6 py-4 border-emerald-500 bg-white rounded shadow"
            >
              {console.log(owner)}
              <p><span className="font-semibold text-gray-700">Owner Name:</span> {owner.username}</p>
              <p><span className="font-semibold text-gray-700">Wallet:</span> {owner.ownerWalletAddress}</p>
              <div className="absolute left-[-10px] top-4 w-4 h-4 rounded-full bg-emerald-500 border-2 border-white"></div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default LandTransferChain;
