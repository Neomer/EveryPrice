using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IPrice : IEntity
    {
        IProduct Product { get; set; }

        double Price { get; set; }

        string Unit { get; set; }

        DateTime CreationDate { get; set; }

    }
}
