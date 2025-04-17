import React, { useEffect, useState } from "react";
import { FaUser, FaWallet, FaKey, FaUserShield } from "react-icons/fa";

function UserDetails() {
  const [walletAddress, setWalletAddress] = useState(
    localStorage.getItem("ownerWalletAddress")
  );
  const [user, setUser] = useState(null);
  const [error, setError] = useState("");

  const fetchUserDetails = async () => {
    try {
      const res = await fetch(`/getUserByWallet?wallet=${walletAddress}`);
      const data = await res.json();

      if (Array.isArray(data) && data.length > 0) {
        setUser(data[0]);
        setError("");
      } else {
        setUser(null);
        setError("User not found");
      }
    } catch (err) {
      setError("Error fetching user details");
      setUser(null);
    }
  };

  useEffect(() => {
    fetchUserDetails();
  }, [walletAddress]);   

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-xl w-full space-y-8">
        {error && <p className="text-red-500 text-center">{error}</p>}

        {user && (
          <div className="bg-white rounded-2xl shadow-lg p-6 space-y-6">
            <div className="flex items-center gap-4">
              <FaUser className="text-3xl text-emerald-500" />
              <div>
                <p className="text-gray-600 text-sm">Username</p>
                <p className="text-xl font-semibold text-gray-900">
                  {user.username}
                </p>
              </div>
            </div>

            <div className="flex items-center gap-4">
              <FaWallet className="text-2xl text-blue-500" />
              <div>
                <p className="text-gray-600 text-sm">Wallet Address</p>
                <p className="font-medium text-gray-900 break-all">
                  {user.ownerWalletAddress}
                </p>
              </div>
            </div>

            <div className="flex items-center gap-4">
              <FaKey className="text-2xl text-yellow-500" />
              <div>
                <p className="text-gray-600 text-sm">Hashed Password</p>
                <p className="text-gray-700 font-mono text-sm break-all">
                  {user.password}
                </p>
              </div>
            </div>

            <div className="flex items-center gap-4">
              <FaUserShield className="text-2xl text-purple-500" />
              <div>
                <p className="text-gray-600 text-sm">Roles</p>
                <div className="flex gap-2 mt-1 flex-wrap">
                  {user.roles.map((role, index) => (
                    <span
                      key={index}
                      className="px-3 py-1 text-sm font-medium rounded-full bg-emerald-100 text-emerald-800"
                    >
                      {role}
                    </span>
                  ))}
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default UserDetails;
