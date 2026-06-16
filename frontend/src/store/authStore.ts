import create from 'zustand';

interface AuthStore {
  user: any | null;
  accessToken: string | null;
  isAuthenticated: boolean;
  login: (user: any, accessToken: string, refreshToken: string) => void;
  logout: () => void;
  setUser: (user: any) => void;
}

export const useAuthStore = create<AuthStore>((set) => ({
  user: null,
  accessToken: null,
  isAuthenticated: false,
  login: (user, accessToken, refreshToken) => {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
    set({ user, accessToken, isAuthenticated: true });
  },
  logout: () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    set({ user: null, accessToken: null, isAuthenticated: false });
  },
  setUser: (user) => set({ user }),
}));