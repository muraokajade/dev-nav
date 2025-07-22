import { useAuth } from "../../context/AuthContext"

export const HomePage = () => {
    const {idToken} = useAuth();
    console.log(idToken);
    return <div>
        <h1 className="text-5xl">
            ここはあなたがお好きに作ってきださい!
        </h1>
    </div>
}