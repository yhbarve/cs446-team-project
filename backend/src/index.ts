// Create a server - Yash
import express, {Request, Response} from 'express';

const app = express();
const PORT = process.env.PORT || 3000;

app.get('/', (req: Request, res: Response) => {
    res.status(200).json({
        msg: "App is up!"
    });
});

app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}`);
});