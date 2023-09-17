import Link from "next/link";

const Header = () => {
    return (
        // <div className='container mx-auto text-center mb-6'>
        //     <p className='text-2xl font-bold'><Link href='/'>EDA Graph</Link></p>
        //     <p className='text-base font-light'>A service to trace inter-app communication through events</p>
        // </div>
        <div
            className="bg-white px-6 py-20 text-center text-neutral-800">
            <h1 className="mb-6 text-5xl font-bold">EDA Graph</h1>
            <h3 className="mb-8 text-3xl font-bold">A service to trace inter-app communication through events</h3>
        </div>
    )
}

export default Header;