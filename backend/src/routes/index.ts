import { Router } from "express";
import userRouter from "./user";
import workoutTemplateRouter from "./workoutTemplate";

const router = Router();

router.use('/user', userRouter);
router.use('/workoutTemplate', workoutTemplateRouter);

export default router;