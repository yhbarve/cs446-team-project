import { PrismaClient, BodyPart } from "@prisma/client";
import prisma from "../lib/prisma";

async function seedWorkoutTemplates() {
  const bodyParts: BodyPart[] = ["ABS", "ARMS", "CARDIO", "CHEST", "LEGS"];

  const DEFAULT_USER_ID = "621b6f5d-aa5d-422b-bd15-87f23724396c";

  await prisma.workoutSession.deleteMany({});
  await prisma.workoutTemplate.deleteMany({});
  console.log("🧹 Cleared all existing workout templates.");

  const templates = [];

  for (const part of bodyParts) {
    const exercises = await prisma.exerciseTemplate.findMany({
      where: {
        bodyPart: part,
        isGeneral: true,
      },
      take: 4,
    });

    if (exercises.length === 0) {
      console.log(`⚠️ No exercises found for ${part}`);
      continue;
    }

    const template = await prisma.workoutTemplate.create({
      data: {
        name: `General ${part} Workout`,
        isGeneral: true,
        exercises: {
          connect: exercises.map((e) => ({ id: e.id })),
        },
      },
      include: { exercises: true },
    });

    templates.push(template);
  }

  for (let i = 1; i <= 2; i++) {
    const customExercises = await prisma.exerciseTemplate.findMany({
      take: 5,
    });

    if (customExercises.length === 0) {
      console.log("⚠️ No exercises found for custom user workout");
      continue;
    }

    const customTemplate = await prisma.workoutTemplate.create({
      data: {
        name: `Custom Workout ${i}`,
        isGeneral: false,
        userId: DEFAULT_USER_ID,
        exercises: {
          connect: customExercises.map((e) => ({ id: e.id })),
        },
      },
      include: { exercises: true },
    });

    templates.push(customTemplate);
  }

  console.log(JSON.stringify(templates, null, 2));
  console.log("✅ Workout templates created.");
}

seedWorkoutTemplates()
  .catch((e) => {
    console.error("❌ Error creating templates:", e);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
