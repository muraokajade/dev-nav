// Firebase Authenticationの「ユーザー情報」の型（User）だけを型としてインポート
import type { User } from "firebase/auth";
//createContext: グローバルでデータを共有するための「コンテキスト本体」を作る関数
//useContext: どの子コンポーネントからでも、そのコンテキスト情報を参照するためのフック
import { createContext, useContext } from "react";

//「認証状態（ログイン情報）」としてグローバルで持ち回すデータ構造の型定義
export interface AuthContextType {
  currentUser: User | null;
  isAuthenticated: boolean;
  loading: boolean;
  idToken: string | null;
  isAdmin: boolean;
}

// 「認証情報」をグローバルで配るためのContext本体を作っています
// createContext: 初期値（未ログイン状態）で作成
export const AuthContext = createContext<AuthContextType>({
  currentUser: null,
  isAuthenticated: false,
  loading: true,
  idToken: null,
  isAdmin: false,
});

//「認証情報（AuthContext）」をどこからでも取得できるカスタムフックです
export const useAuth = () => useContext(AuthContext);
