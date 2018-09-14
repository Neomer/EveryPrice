using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Price : BaseEntity, IPrice
    {
        public virtual double Value { get; set; }

        public virtual string Unit { get; set; }

        public virtual DateTime CreationDate { get; set; }

        public virtual IProduct Product { get; set; }

        public virtual IUser Creator { get; set; }

    }
}
