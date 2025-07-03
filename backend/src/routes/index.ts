import { Router } from "express";
import userRouter from "./user";
import workoutTemplateRouter from "./workoutTemplate";
import exerciseTemplateRouter from "./exerciseTemplate";

const router = Router();

router.use('/user', userRouter);
router.use('/workoutTemplate', workoutTemplateRouter);
router.use('/exercises', exerciseTemplateRouter);

export default router;