import { Router } from "express";
import userRouter from "./user";
import exerciseRouter from "./exerciseTemplate";
import workoutRouter from "./workoutTemplate";
import precordRouter from "./pr";
import matchRouter from "./exerciseSession";

const router = Router();

router.use('/user', userRouter);
router.use('/exercises', exerciseRouter);
router.use('/workout', workoutRouter);
router.use('/pr', precordRouter);
router.use('/match', matchRouter);

export default router;