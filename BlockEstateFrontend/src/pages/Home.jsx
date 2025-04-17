import React from "react";
import { useNavigate } from "react-router-dom";
import { ShieldCheck, Globe, Sparkles } from "lucide-react";

function Home() {
  const navigate = useNavigate();

  return (
    <div className="bg-gray-100 min-h-[90vh]">
      {/* Hero Section */}
      <section className="flex flex-col lg:flex-row items-center justify-center gap-10 px-6 md:px-16 lg:px-24 xl:px-32 py-16">
        {/* Image */}
        <div className="lg:w-1/2 w-full">
          <img
            className="rounded-2xl shadow-lg"
            src="https://plus.unsplash.com/premium_photo-1661899405263-a0bee333068e?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            alt="blockchain-land"
          />
        </div>

        {/* Text Content */}
        <div className="lg:w-1/2 w-full">
          <h1 className="text-4xl md:text-5xl font-bold leading-tight text-gray-800 mb-6">
            Secure Your Land{" "}
            <span className="text-secondary">with Blockchain</span>
          </h1>
          <p className="text-lg text-gray-600 mb-8">
            BlockEstate brings trustless land transfers and verified ownership
            to your fingertips. Experience real estate redefined with the power
            of Web3.
          </p>
          <div className="flex flex-wrap gap-4">
            <button
              onClick={() => navigate("/registerland")}
              className="bg-emerald-500 hover:bg-emerald-600 text-white px-6 py-3 rounded-full text-base font-medium transition"
            >
              Get Started
            </button>
            <button
              onClick={() => navigate("/landdetails")}
              className="bg-white border border-secondary text-secondary hover:bg-secondary hover:text-white px-6 py-3 rounded-full text-base font-medium transition"
            >
              Learn More
            </button>
          </div>
        </div>
      </section>

      {/* Feature Section */}
      <section className="bg-white py-16 px-6 md:px-16 lg:px-24 xl:px-32">
        <h2 className="text-3xl font-bold text-center text-darkText mb-12">
          Why Choose BlockEstate?
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-10">
          <FeatureCard
            icon={<ShieldCheck size={40} className="text-secondary" />}
            title="Verified Ownership"
            desc="Immutable blockchain records eliminate disputes and fraud. Own your land with peace of mind."
          />
          <FeatureCard
            icon={<Globe size={40} className="text-secondary" />}
            title="Global Access"
            desc="Buy, sell, or transfer land across borders seamlessly—powered by decentralized tech."
          />
          <FeatureCard
            icon={<Sparkles size={40} className="text-secondary" />}
            title="Instant Transfers"
            desc="No more lengthy paperwork. Transfer ownership instantly through smart contracts."
          />
        </div>
      </section>
    </div>
  );
}

function FeatureCard({ icon, title, desc }) {
  return (
    <div className="bg-gray-50 p-8 rounded-2xl shadow hover:shadow-md transition-all text-center">
      <div className="flex justify-center mb-4">{icon}</div>
      <h3 className="text-xl font-semibold mb-2 text-gray-800">{title}</h3>
      <p className="text-gray-600 text-sm">{desc}</p>
    </div>
  );
}

export default Home;
