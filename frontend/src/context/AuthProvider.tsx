import { onAuthStateChanged } from "firebase/auth";
import type { User } from "firebase/auth";
import type { ReactNode } from "react";
import {
  useState,
  useEffect,
} from "react";
import { auth } from "../libs/firebase"; 
import type { AuthContextType } from "./AuthContext";

// Context本体をimportして利用
import { AuthContext } from "./AuthContext"; 

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [idToken, setIdToken] = useState<string | null>(null);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (firebaseuser) => {
      setCurrentUser(firebaseuser);
      if (firebaseuser) {
        const token = await firebaseuser.getIdToken();
        setIdToken(token);
        const tokenResult = await firebaseuser.getIdTokenResult();
        setIsAdmin(tokenResult.claims.admin === true);
      } else {
        setIdToken(null);
        setIsAdmin(false);
      }
      setLoading(false);
    });
    return () => unsubscribe();
  }, []);

  const value: AuthContextType = {
    currentUser,
    isAuthenticated: !!currentUser,
    loading,
    idToken,
    isAdmin,
  };

  return (
    <AuthContext.Provider value={value}>
      {children} 
    </AuthContext.Provider>
  );
};
