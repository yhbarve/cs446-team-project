import { Router } from "express";
import userRouter from "./user";
import workoutTemplateRouter from "./workoutTemplate";
import exerciseTemplateRouter from "./exerciseTemplate";
import exerciseSessionRouter from "./exerciseSession";

const router = Router();

router.use("/user", userRouter);
router.use("/workoutTemplate", workoutTemplateRouter);
router.use("/exercise-template", exerciseTemplateRouter);
router.use("/exercise-sessions", exerciseSessionRouter);

export default router;
