import { Router, Request, Response } from "express";
import prisma from "../lib/prisma";

const userRouter = Router();

// Create new user
userRouter.post("/", async (req: Request, res: Response) => {
	const {
		email,
		name,
		passwordHash,
		heightCm,
		weightKg,
		age,
		location,
		timePreference,
		experienceLevel,
		gymFrequency,
	} = req.body;

	try {
		const newUser = await prisma.user.create({
			data: {
				email,
				name,
				passwordHash,
				heightCm,
				weightKg,
				age,
				location,
				timePreference,
				experienceLevel,
				gymFrequency,
			},
		});

		res.status(201).json(newUser);
	} catch (err: any) {
		res.status(400).json({ error: err.message });
	}
});

userRouter.get("/", async (req: Request, res: Response) => {
	try {
		const users = await prisma.user.findMany();
		res.status(200).json(users);
	} catch (err: any) {
		res.status(500).json({ error: err.message });
	}
});

// Get user by ID
userRouter.get("/:id", async (req: Request, res: Response): Promise<any> => {
	const userId = req.params.id;

	try {
		const user = await prisma.user.findUnique({
			where: { id: userId },
		});

		if (!user) {
			return res.status(404).json({ error: "User not found." });
		}

		res.json(user);
	} catch (err: any) {
		res.status(500).json({ error: err.message });
	}
});

// Update user
userRouter.put("/:id", async (req: Request, res: Response) => {
	const userId = req.params.id;
	const updateData = req.body;

	try {
		const updatedUser = await prisma.user.update({
			where: { id: userId },
			data: updateData,
		});

		res.json(updatedUser);
	} catch (err: any) {
		res.status(400).json({ error: err.message });
	}
});

// Delete user
userRouter.delete("/:id", async (req: Request, res: Response) => {
	const userId = req.params.id;

	try {
		await prisma.user.delete({
			where: { id: userId },
		});

		res.status(204).send(); // success with no content
	} catch (err: any) {
		res.status(400).json({ error: err.message });
	}
});

export default userRouter;
