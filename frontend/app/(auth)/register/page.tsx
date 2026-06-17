'use client'

import AuthFormContainer from "@/components/auth/AuthFormContainer";
import { RegisterInput } from "@/validation/auth";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Mail, LockIcon, ArrowRight, User2 } from "lucide-react";
import { registerSchema } from "@/validation/auth";
import { useAuth } from "@/context/AuthContext";
import { useRouter } from "next/navigation";
import { registerWithEmailAndPwd } from "@/api/login";

const RegisterPage = () => {
    const { register, handleSubmit, reset, formState: { errors, isValid, isSubmitting } } = useForm<RegisterInput>({ resolver: zodResolver(registerSchema), mode: 'onChange' })

    const { login } = useAuth()
    const router = useRouter()

    const handleRegister = async (data: RegisterInput) => {
        const authResponse = await registerWithEmailAndPwd(data.name, data.email, data.password)
        login(authResponse.accessToken)
        router.push("/dashboard")
    }

    return (
        <AuthFormContainer title={"Join Us"} subtitle={"Start your expense management journey"} footerText={"Already have an account?"} footerAction={"Log in"} footerLink={"/login"}>
            <form className="my-2" onSubmit={handleSubmit(handleRegister)}>
                <div className="my-2">
                    <label className="text-sm font-medium text-slate-400 ml-1">Name</label>
                    <div className="relative">
                        <User2 className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500" size={18} />
                        <input {...register('name')}
                            placeholder="John Doe" className="w-full bg-slate-950/50 border border-slate-800 rounded-xl py-3 pl-12 pr-4 text-white outline-none focus:border-purple-500 transition-all" />
                        {errors.name?.message && <p>{errors.name?.message}</p>}
                    </div>
                </div>
                <div className="my-2">
                    <label className="text-sm font-medium text-slate-400 ml-1">Email Address</label>
                    <div className="relative">
                        <Mail className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500" size={18} />
                        <input {...register('email')}
                            placeholder="user@example.com" className="w-full bg-slate-950/50 border border-slate-800 rounded-xl py-3 pl-12 pr-4 text-white outline-none focus:border-purple-500 transition-all" />
                        {errors.email?.message && <p>{errors.email?.message}</p>}
                    </div>
                    <div className="my-2">
                        <label className="text-sm font-medium text-slate-400">Password</label>
                        <div className="relative">
                            <LockIcon className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500" size={18} />
                            <input {...register('password')} type="password"
                                placeholder="•••••••" className="w-full bg-slate-950/50 border border-slate-800 rounded-xl py-3 pl-12 pr-4 text-white outline-none focus:border-purple-500 transition-all" />
                            {errors.password?.message && <p>{errors.password?.message}</p>}
                        </div>
                    </div>
                    <button className="w-full enabled:bg-purple-600 hover:bg-purple-500 text-white font-bold py-3 rounded-xl flex items-center justify-center gap-2 shadow-lg shadow-purple-500/20 transition-all disbled:opacity-50" type="submit" disabled={!isValid || isSubmitting}>
                        Register <ArrowRight size={18} />
                    </button>
                </div>
            </form>
        </AuthFormContainer>
    )
}

export default RegisterPage;