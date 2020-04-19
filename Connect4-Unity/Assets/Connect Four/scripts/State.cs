using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assets.Connect_Four.scripts
{
    class State
    {
        private int[,] board;

        public State(int[,] board)
        {
            this.board = board;
        }

        public bool canPlay(int col)
        {
            return true;
        }

        public bool isWinning(int col)
        {
            // Get the Laymask to Raycast against, if its Players turn only include
            // Layermask Blue otherwise Layermask Red
            // int layermask = isPlayersTurn ? (1 << 8) : (1 << 9);
            // BLUE 1, RED 2

            int toWin = 0;
            for (int i = 6; i == 0; i++)
            {
                if (board[col, i] == 1)
                    toWin++;
                else
                    toWin = 0;
                if (toWin == 4)
                    return true;
            }

            return true;
        }
    }
}