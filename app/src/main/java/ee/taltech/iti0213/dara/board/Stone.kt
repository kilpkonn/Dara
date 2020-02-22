package ee.taltech.iti0213.dara.board

enum class Stone : IStone{
    EMPTY {
        override fun isEmpty(): Boolean {
           return true
        }
    }, WHITE {
        override fun isWhite(): Boolean {
            return true
        }
    }, BLACK {
        override fun isBlack(): Boolean {
            return true
        }
    };

    override fun isEmpty(): Boolean {
       return false
    }

    override fun isWhite(): Boolean {
        return false
    }

    override fun isBlack(): Boolean {
        return false
    }
}
