import { useEffect, useState } from 'react';
import { useAppContext } from '../context/AppContext';

const LandDetails = () => {
  // const { user ,isAuthenticated } = useAppContext();
  const [lands, setLands] = useState([]);
  const [loading, setLoading] = useState(true);
  const userid = localStorage.getItem("userid")
  console.log(userid)

  useEffect(() => {
    console.log(userid)



    const fetchLands = async () => {
      if (!userid) return;

      try {
        const res = await fetch(`http://localhost:8080/User/getLandDetailsById?id=${userid}`);
        const data = await res.json();
        console.log(data)
        setLands(data);
      } catch (error) {
        console.error('Error fetching lands:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchLands();
  }, []);

  if (loading) {
    return <div className="text-center mt-20 text-gray-500">Loading land data...</div>;
  }

  if (!lands.length) {
    return <div className="text-center mt-20 text-gray-500">No registered lands found.</div>;
  }

  return (
    <div className="max-w-6xl mx-auto px-4 py-16">
      <h1 className="text-3xl font-bold mb-8 text-darkText">Your Registered Lands</h1>

      <div className="grid gap-6 md:grid-cols-2">
        {lands.map((land) => (
          <div
            key={land.id}
            className="bg-white p-6 rounded-2xl shadow-md border border-gray-200"
          >
            <h2 className="text-xl font-semibold mb-2 text-primary">
              Property ID: {land.propertyId || 'N/A'}
            </h2>
            <p><span className="font-medium">Location:</span> {land.locationDescription}</p>
            <p><span className="font-medium">Coordinates:</span> {land.latitudeLongitude}</p>
            <p><span className="font-medium">Area:</span> {land.areaSqMeters} sq.m</p>
            <p><span className="font-medium">Token ID:</span> {land.tokenId ?? 'Not Minted'}</p>
            <p><span className="font-medium">Registered On:</span>{' '}
              {new Date(land.registrationDate).toLocaleString()}
            </p>
            {land.tokenURI && (
              <a
                href={land.tokenURI}
                target="_blank"
                rel="noopener noreferrer"
                className="mt-4 inline-block text-secondary underline"
              >
                View Token Metadata
              </a>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default LandDetails;
