import { NextFunction, Request, Response } from "express";
import jwt from "jsonwebtoken"

interface AuthRequest extends Request {
    user?: any
}

export default function authMiddleware(req: AuthRequest, res: Response, next: NextFunction): void {
    const authHeader = req.headers.authorization;
    const token = authHeader?.split(' ')[1];

    if (!token){
        res.status(401).json({ message: "Unauthorized: No token provided" });
        return;
    }

    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET!);
        req.user = decoded;
        next();
    } catch (err) {
        res.status(401).json({ message: "Unauthorized: Invalid token" });
        return;
    }
}