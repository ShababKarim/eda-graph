import './globals.css'
import type {Metadata} from 'next'
import {Montserrat} from 'next/font/google'
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";

const montserrat = Montserrat({weight: ["200", "400", "700"], subsets: ['latin']})

export const metadata: Metadata = {
    title: 'EDA Graph',
    description: 'A service to trace inter-app communication through events',
}

export default function RootLayout({children}: { children: React.ReactNode }) {
    return (
        <html lang="en">
            <body className={montserrat.className}>
                <Header/>
                {children}
                <Footer />
            </body>
        </html>
    )
}
