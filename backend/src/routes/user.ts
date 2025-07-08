import { Router, Request, Response } from "express";
import prisma from "../lib/prisma";
import { TimePreference, ExperienceLevel, GymFrequency } from "../lib/types";
import bcrypt from "bcrypt";

const userRouter = Router();

const SALT_ROUNDS = 10;

userRouter.get('/', (req: Request, res: Response) => {
	res.status(200).json({
		msg: "Hi from user router"
	});
});

// Create new user
userRouter.post("/signup", async (req: Request, res: Response): Promise<any> => {
	const {
		email,
		name,
		password,
		heightCm,
		weightKg,
		age,
		location,
		timePreference,
		experienceLevel,
		gymFrequency,
	} = req.body;

	// Validate enums
	if (!Object.values(TimePreference).includes(timePreference)) {
		return res.status(400).json({
			error: `Invalid timePreference. Must be one of: ${Object.values(
				TimePreference
			).join(", ")}.`,
		});
	}

	if (!Object.values(ExperienceLevel).includes(experienceLevel)) {
		return res.status(400).json({
			error: `Invalid experienceLevel. Must be one of: ${Object.values(
				ExperienceLevel
			).join(", ")}.`,
		});
	}

	if (!Object.values(GymFrequency).includes(gymFrequency)) {
		return res.status(400).json({
			error: `Invalid gymFrequency. Must be one of: ${Object.values(
				GymFrequency
			).join(", ")}.`,
		});
	}

	try {
		const existingUser = await prisma.user.findUnique({
			where: { email },
		});

		if (existingUser) {
			return res.status(400).json({ error: "Email already in use." });
		}

		const hashedPassword = await bcrypt.hash(password, SALT_ROUNDS);
		const newUser = await prisma.user.create({
			data: {
				email,
				name,
				passwordHash: hashedPassword,
				heightCm,
				weightKg,
				age,
				location,
				timePreference: timePreference as TimePreference,
				experienceLevel: experienceLevel as ExperienceLevel,
				gymFrequency: gymFrequency as GymFrequency,
			},
		});

		const { passwordHash: _, ...userWithoutPassword } = newUser;
		res.status(201).json(userWithoutPassword);
	} catch (err: any) {
		res.status(400).json({ error: err.message });
	}
});

userRouter.get("/users", async (req: Request, res: Response) => {
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

userRouter.post("/login", async (req: Request, res: Response): Promise<any> => {
	const { email, password } = req.body;

	if (!email || !password) {
		return res.status(400).json({ error: "Email and password are required." });
	}

	try {
		const user = await prisma.user.findUnique({
			where: { email },
		});

		if (!user) {
			return res.status(401).json({ error: "Invalid credentials." });
		}

		const passwordMatch = await bcrypt.compare(password, user.passwordHash);

		if (!passwordMatch){
			return res.status(401).json({ error: "Invalid credentials." });
		}

		const { passwordHash, ...userWithoutPassword } = user;
		res.status(200).json(userWithoutPassword);
	} catch (err: any) {
		res.status(500).json({ error: err.message });
	}
});

export default userRouter;
