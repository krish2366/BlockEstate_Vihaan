import { useContext , createContext , useState } from "react";

const AppContext = createContext();

export const AppProvider = ({ children }) =>{
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState({});

    return (
        <AppContext.Provider value={{isAuthenticated, setIsAuthenticated, user, setUser}}>
            {children}
        </AppContext.Provider>
    )
}

export const useAppContext = () => useContext(AppContext);