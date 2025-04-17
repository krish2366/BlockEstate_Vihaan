import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();
  const [isAutheticated, setIsAutheticated] = useState(
    localStorage.getItem("isAuthenticated")
  );

  return (
    <div>
      <nav className="h-[70px] relative w-full px-6 md:px-16 lg:px-24 xl:px-32 flex items-center justify-between z-20 bg-white text-gray-700 shadow-[0px_4px_25px_0px_#0000000D] transition-all">
        <Link to="/" className="text-3xl font-bold text-gray-800">
          BlockEstate
        </Link>
        <ul className="md:flex hidden items-center gap-10">
          <li>
            <Link className="hover:text-gray-500/80 transition" to="/">
              Home
            </Link>
          </li>
          <li>
            <Link
              className="hover:text-gray-500/80 transition"
              to="/registerland"
            >
              Register Land
            </Link>
          </li>
          <li>
            <Link
              className="hover:text-gray-500/80 transition"
              to="/transferland"
            >
              Transfer Land
            </Link>
          </li>
          <li>
            <Link
              className="hover:text-gray-500/80 transition"
              to="/landdetails"
            >
              Land Details
            </Link>
          </li>
        </ul>

        {isAutheticated ? (
          <button 
            className="bg-white text-gray-600 border border-gray-300 md:inline hidden text-sm hover:bg-gray-50 active:scale-95 transition-all w-24 h-11 rounded-full"
            onClick={() => navigate("/userDetails")}
          >
            Profile
          </button>
        ) : (
          <button
            type="button"
            onClick={() => navigate("/login")}
            className="bg-white text-gray-600 border border-gray-300 md:inline hidden text-sm hover:bg-gray-50 active:scale-95 transition-all w-24 h-11 rounded-full"
          >
            Login
          </button>
        )}

        <button
          aria-label="menu-btn"
          type="button"
          className="menu-btn inline-block md:hidden active:scale-90 transition"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width={30}
            height={30}
            viewBox="0 0 30 30"
            fill="#000"
          >
            <path d="M 3 7 A 1.0001 1.0001 0 1 0 3 9 L 27 9 A 1.0001 1.0001 0 1 0 27 7 L 3 7 z M 3 14 A 1.0001 1.0001 0 1 0 3 16 L 27 16 A 1.0001 1.0001 0 1 0 27 14 L 3 14 z M 3 21 A 1.0001 1.0001 0 1 0 3 23 L 27 23 A 1.0001 1.0001 0 1 0 27 21 L 3 21 z" />
          </svg>
        </button>
        <div className="mobile-menu absolute top-[70px] left-0 w-full bg-white p-6 hidden md:hidden">
          <ul className="flex flex-col space-y-4 text-lg">
            <li>
              <Link to="#" className="text-sm">
                Home
              </Link>
            </li>
            <li>
              <Link to="#" className="text-sm">
                Register Land
              </Link>
            </li>
            <li>
              <Link to="#" className="text-sm">
                Transfer Land
              </Link>
            </li>
            <li>
              <Link to="#" className="text-sm">
                Land Details
              </Link>
            </li>
          </ul>
          <button
            type="button"
            className="bg-white text-gray-600 border border-gray-300 mt-6 text-sm hover:bg-gray-50 active:scale-95 transition-all w-40 h-11 rounded-full"
          >
            Login
          </button>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
