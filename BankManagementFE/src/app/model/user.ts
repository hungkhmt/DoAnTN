export interface User {
    userId: string;
    fullname: string;
    dateOfBirth: string;
    phoneNumber: string;
    address: string;
    username: string;
    password: string;
    email: string;
    created_at: string;
    roles: Role[];
}

export interface Role {
    roleId: string,
    roleName: string;
}