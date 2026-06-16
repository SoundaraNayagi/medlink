'use client';

import { useAuthStore } from '@/store/authStore';

export default function DashboardPage() {
  const user = useAuthStore((state) => state.user);

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-8">Welcome {user?.firstName}</h1>
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-lg font-semibold">Role</h2>
            <p className="text-2xl text-blue-600">{user?.role}</p>
          </div>
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-lg font-semibold">Email</h2>
            <p className="text-sm text-gray-600">{user?.email}</p>
          </div>
        </div>
      </div>
    </div>
  );
}