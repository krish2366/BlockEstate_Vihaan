import React from 'react'

function Page404() {
  return (
    <div className='flex flex-col items-center justify-center h-[90vh] bg-gray-100'>
      <h1 className='text-center text-4xl font-bold'>Page Not Found</h1>
      <p className='text-center text-2xl font-semibold'>The page you are looking for does not exist.</p>
      <a className='text-center text-xl font-medium underline' href="/">Return Home</a>
    </div>
  )
}

export default Page404
