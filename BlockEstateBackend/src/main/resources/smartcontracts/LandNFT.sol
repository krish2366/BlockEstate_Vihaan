// SPDX-License-Identifier: MIT
pragma solidity ^0.8.26;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract LandOwnershipNFT is ERC721URIStorage, Ownable {
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;

    struct LandDetails {
        string propertyId;
        string locationDescription;
        string latitudeLongitude;
        uint256 areaSqMeters;
        address registeredOwner;
        uint256 registrationDate;
    }

    mapping(uint256 => LandDetails) private _landDetails;
    // Removed the _registeredPropertyIds mapping to eliminate uniqueness checks

    event LandRegistered(uint256 indexed tokenId, string propertyId, address owner);
    event LandTransferred(uint256 indexed tokenId, address indexed from, address indexed to);

    constructor() ERC721("Real Estate Land Ownership", "RELO")
//    Ownable(msg.sender)
    {}

    function registerLand(
        address to,
        string memory tokenURI,
        string memory propertyId,
        string memory locationDescription,
        string memory latitudeLongitude,
        uint256 areaSqMeters
    ) public onlyOwner returns (uint256) {
        // Removed the property ID uniqueness check
        _tokenIds.increment();
        uint256 newTokenId = _tokenIds.current();

        _mint(to, newTokenId);
        _setTokenURI(newTokenId, tokenURI);

        _landDetails[newTokenId] = LandDetails({
            propertyId: propertyId,
            locationDescription: locationDescription,
            latitudeLongitude: latitudeLongitude,
            areaSqMeters: areaSqMeters,
            registeredOwner: to,
            registrationDate: block.timestamp
        });

        emit LandRegistered(newTokenId, propertyId, to);

        return newTokenId;
    }

    function getLandDetails(uint256 tokenId) public view returns (LandDetails memory) {
        require(_exists(tokenId), "LandOwnershipNFT: Land does not exist");
        return _landDetails[tokenId];
    }

    function updateGeolocation(uint256 tokenId, string memory newLatitudeLongitude) public {
        require(ownerOf(tokenId) == msg.sender, "LandOwnershipNFT: Only owner can update geolocation");
        _landDetails[tokenId].latitudeLongitude = newLatitudeLongitude;
    }

    function _exists(uint256 tokenId) internal view override returns (bool) {
        return ownerOf(tokenId) != address(0);
    }

    // Simple helper function to update registered owner when tokens are transferred
    function updateRegisteredOwner(uint256 tokenId, address newOwner) internal {
        _landDetails[tokenId].registeredOwner = newOwner;
    }

    // These functions are externally callable to keep the land registry updated with ownership changes
    function transferLand(address to, uint256 tokenId) public {
        transferFrom(msg.sender, to, tokenId);
        updateRegisteredOwner(tokenId, to);
        emit LandTransferred(tokenId, msg.sender, to);
    }

    function safeTransferLand(address to, uint256 tokenId) public {
        safeTransferFrom(msg.sender, to, tokenId);
        updateRegisteredOwner(tokenId, to);
        emit LandTransferred(tokenId, msg.sender, to);
    }

    function getFullLandInfo(uint256 tokenId) public view returns (
        string memory uri,
        LandDetails memory details
    ) {
        require(_exists(tokenId), "Token does not exist");
        return (tokenURI(tokenId), _landDetails[tokenId]);
    }
}
